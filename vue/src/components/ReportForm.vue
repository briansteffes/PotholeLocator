<template>
  <div id="report">
    <h1>Report a Pothole</h1>
    <p class="status-message error" v-show="errorMsg !== ''">{{ errorMsg }}</p>
    <form @submit.prevent="submitPothole">
      <div class="reportform">
        <div>
          <input
            type="text"
            id="potholeName"
            class="form-control"
            placeholder="Name"
            v-model="pothole.potholeName"
            required
            autofocus
          />
        </div>
        <div>
          <input
            type="text"
            id="potholeLat"
            class="form-control"
            placeholder="Latitude"
            v-model="pothole.potholeLat"
            required
            autofocus
          />
        </div>
        <div>
          <input
            type="text"
            id="potholeLong"
            class="form-control"
            placeholder="Longitude"
            v-model="pothole.potholeLong"
            required
            autofocus
          />
        </div>
        <!-- <div>
                  <button @click.prevent="uploadImage" class="photo-button">Attach Photo</button>
              </div> -->
        <div class="upload">
          <label for="formFileLg" class="form-label">Attach a photo (optional)</label>
          <input
            class="form-control form-control-lg"
            id="formFileLg"
            type="file"
          />
        </div>
        <div class="submit">
          <button type="submit" class="btn btn-primary btn-lg btn-lg">Submit</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import potholeService from '../services/PotholeService';

export default {
    name: "report-form",
    // components: {
        
    // },
    data() {
        return {
            pothole: {
                potholeName: '',
                potholeLat: '',
                potholeLong: '',
                accountId: this.$store.state.userAccount.accountId
            },
            isLoading: true,
            errorMsg: ""
        };
    },
    methods: {
        uploadImage() {
            console.log('test!');
        },
        submitPothole() {
            potholeService
            .createPothole(this.pothole)
            .then(response => {
                if (response.status === 201) {
                    this.$router.push({
                    path: '/view-pothole',
                    query: { registration: 'success' },
                    });
                }
            })
            .catch(error => {
                this.handleErrorResponse(error, "adding");
            })
        },
        handleErrorResponse(error, verb) {
        if (error.response) {
            this.errorMsg = `Error ${verb} pothole. Response received was ${error.response.statusText}.`;
        } else if (error.request) {
            this.errorMsg = `Error ${verb} pothole. Server could not be reached.`;
        } else {
            this.errorMsg = `Error ${verb} pothole. Request could not be created.`;
        }
    }
    }
}
</script>

<style scoped>
#report {
  display: flex;
  flex-direction: column;
  margin-top: 3em;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

.reportform {
  padding-top: 2em;
  margin: auto;
  width: 24em;
}

.form-label {
  color: #fefefe;
  font-family: "Montserrat", sans-serif;
}

.form-control-lg {
  height: 2em;
  width: 19em;
}

#potholeName, #potholeLat, #potholeLong {
  padding-left: 1em;
}

.btn {
  margin-top: 2em;
}

.upload {
  margin-top: 2em;
}

/*
.photo-button {
    color: #0d6efd;
    background-color: #fffffe;
    border: 1px solid #fffffe;
    border-radius: .3em;
    font-family: 'Montserrat', sans-serif;
}
*/
</style>