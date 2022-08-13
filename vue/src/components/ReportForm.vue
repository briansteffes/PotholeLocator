<template>
    <div>
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
                <button @click.prevent="uploadImage">Attach Photo</button>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </form>
    </div>
</template>

<script>
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
            },
            isLoading: true,
            errorMsg: ""
        };
    },
    methods: {
        uploadImage() {
            console.log('test!');
        }
    }
}
</script>

<style>

</style>