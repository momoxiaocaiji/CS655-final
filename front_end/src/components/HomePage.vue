<template>
  <div id="body_ele" style="display: flex; flex-direction: column; justify-content: space-evenly; height: 100%;">
    <!-- The title part -->
    <div id="body_header" style="display: flex; justify-content: space-evenly; align-items: center;">
      <img src="../../img/icon.png" alt="Icon" style="max-width: 150px; max-height: 150px;"/>
      <p style="font-size: 40px; font-weight: 700;">Md5 Cracker</p>
    </div>
    
    <!-- The input and submit button part -->
    <div id="body_input" style="display: flex; justify-content: center; align-items: center; width: 100%;margin-top: 5%;">
      <el-form :inline="true" :model="encry_data" ref="encry_data" size="medium"
          :rules="rules">
        <el-form-item label="Encryption" label-width="80px" prop="encryption">
          <!-- Input box -->
          <el-input v-model="encry_data.encryption" placeholder="Please enter md5 encryption" maxlength="32" clearable></el-input>
        </el-form-item>
        <el-form-item label="Worker number" prop="workNum">
          <!-- Select number of the workers -->
          <el-select  v-model="encry_data.workNum" placeholder="0">
            <el-option label="1" value="1"></el-option>
            <el-option label="2" value="2"></el-option>
            <el-option label="3" value="3"></el-option>
            <el-option label="4" value="4"></el-option>
            <el-option label="5" value="5"></el-option>
            <el-option label="6" value="6"></el-option>
            <el-option label="7" value="7"></el-option>
            <el-option label="8" value="8"></el-option>
            <el-option label="9" value="9"></el-option>
            <el-option label="10" value="10"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <!-- Choose use cache or not -->
          <el-switch
            v-model="encry_data.useCache"
            active-text="Use cache">
          </el-switch>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" type="primary" @click="send_password('encry_data')">Start cracking!!</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-divider></el-divider>
    <!-- Output result -->
    <div v-if="loading_init"></div>
    <div v-else id="body_result" style="display: flex; align-items: center; justify-content: center; height: 30%">
      <div v-if="is_loading" style="display: flex; justify-content: space-evenly; align-items: center; width: 75%; margin-bottom: 20%;">
        <i class="el-icon-loading" style="font-size: 75px;"></i>
        <p style="font-size:50px;">System processing...</p>
      </div>
      <div v-else style="font-size: 30px; display: flex; flex-direction: column; justify-content: space-evenly; margin-bottom: 15%;">
        <div style="display: flex;">
          <p>Decoding result of your encryption is:&nbsp</p>
          <p style="font-weight: 800;"> {{res_password}} </p>
        </div>
        <div style="display: flex;">
          <p>Cracking time is:&nbsp</p>
          <p style="font-weight: 800;"> {{res_time}}ms. </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Mock from 'mockjs'
  import axios from 'axios'

  export default {
    name: 'HomePage',
    data () {
      return {
        is_loading: false,        // loading flag
        loading_init: true,       // default 
        res_password: "aaaaa",    // store decoded password
        res_time: "aaaaa",        // store time cost
        encry_data: {
          encryption: "",         // Input encoded password
          workNum: "",            // Number of workers
          useCache: false,        // Use cache flag
        },
        rules: {                  // Use rules to check the inputs
          encryption: [
            { required: true, message: "Encryption can't be empty", trigger: 'blur'},
            { len: 32, message: 'Length should be 32', trigger: 'blur' },
            { required: true,
              pattern: /^[a-zA-Z0-9]{32}$/,     
              message: 'Can only enter letters or numbers',
              trigger: 'blur' }
          ],
          workNum: [
            { required: true, message: "Required", trigger: 'blur'},
          ]
        }
      }
    },
    methods: {
      // The function used to send the encryption after pressing the button
      send_password(formName) {
        // Check input
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            alert('error submit!!');
            return false;
          } else {
            this.loading_init = false
            // Initialize start time stamp
            var timestamp_start = new Date().getTime()
            this.is_loading = true
            // Post to the server
            axios({
              method: "POST",
              url: '/post_password',
              responseType: "json",
              timeout: 2000000,
              data: JSON.stringify(this.encry_data),
              headers: {
                'content-type': 'application/json'
              }
            }).then(res=>{
              this.is_loading = false
              this.res_password = res.data
              var timestamp_end = new Date().getTime()
              this.res_time = (timestamp_end-timestamp_start)
            })
          }
        });
      }
    },
    watch: {

    }
  }
</script>

<style>
  #body_ele {

  }
  #passcode_title {
    margin: 5px 10px;
  }
  #passcode_input {
    margin: 5px 10px;
  }
  .el-form {
    width: 90%;
    display: flex;
    justify-content: space-evenly;
  }
  .el-input {
    width: 230px;
  }
  .el-select .el-input{
    width: 55px;
  }
</style>
