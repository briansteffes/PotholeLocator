<template>
    <div id="report">
        <p class="status-message error" v-show="errorMsg !== ''">{{errorMsg}}</p>
        <form @submit.prevent="submitPothole">
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
            <div>
                <button @click.prevent="uploadImage" class="photo-button">Attach Photo</button>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </form>
    </div>
</template>

<script>
import potholeService from '../services/PotholeService';
import accountService from '../services/AccountService';
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
                accountId: '',
                imageId: '',
                categoryId: '',
                statusId: '',
                active: true
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
            if (this.$store.state.userAccount.accountId === undefined) {
                accountService.getAccount(this.$store.state.user.id)
                .then(response => {
                    if (response.status === 200) {
                        console.log(this.pothole);
                        this.pothole.accountId = response.data.accountId;
                        this.$store.commit("SET_USER_ACCOUNT", response.data);
                    }
                })
                .catch(error => {
                    this.handleErrorResponse(error, "adding");
                })
            } else {
                this.pothole.accountId = this.$store.state.userAccount.accountId;
            }
            
            console.log(this.$store.state.userAccount.accountId);
            console.log(this.pothole.accountId);

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
            this.errorMsg = `Error ${verb} card. Response received was ${error.response.statusText}.`;
        } else if (error.request) {
            this.errorMsg = `Error ${verb} card. Server could not be reached.`;
        } else {
            this.errorMsg = `Error ${verb} card. Request could not be created.`;
        }
    }
    }
}
</script>

<style scoped>

.photo-button {
    color: #0d6efd;
    background-color: #fffffe;
    border: 1px solid #fffffe;
    border-radius: .3em;
    font-family: 'Montserrat', sans-serif;
}

form, button {
    width: 28em;
}

p {
    color: #fffffe;
    font-family: 'Montserrat', sans-serif;
}

</style>