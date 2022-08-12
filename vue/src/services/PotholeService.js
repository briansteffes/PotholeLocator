import axios from 'axios';


const http = axios.create({
    baseURL: "http://localhost:9000/pothole"
})
export default {
    list(pothole) {
          return http.get('/' + pothole.Id);
    },

    listLocation(location) {
        return http.get('/' + location);
    },

    listReport(report) {
        return http.get('/' + report);
    },

}
//things to grab 
//Location
//Name of Pothole
//User Created Image