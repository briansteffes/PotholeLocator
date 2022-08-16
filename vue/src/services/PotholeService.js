import axios from 'axios';

export default {

    getAllPotholes() {
        return axios.get('/pothole');
    },

    createPothole(pothole) {
        return axios.post('/pothole/report', pothole);
    },

    list(pothole) {
        return axios.get(`/pothole/${pothole.Id}`);
    },

    // listLocation(location) {
    //     return axios.get('/pothole/', location);
    // },

    deletePotholeById(potholeId) {
        return axios.delete(`/pothole/${potholeId}`);
    }

    // listReport(report) {
    //     return axios.get('/pothole/' + report);
    // },

}
//things to grab 
//Location
//Name of Pothole
//User Created Image