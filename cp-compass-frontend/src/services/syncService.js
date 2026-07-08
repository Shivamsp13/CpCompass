import api from "./api";

const syncService = {

    async syncCodeforces() {

        const response = await api.post(
            "/sync"
        );

        return response.data;

    }

};

export default syncService;