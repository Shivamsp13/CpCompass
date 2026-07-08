import api from "./api";

const contestHistoryService = {

    async getContestHistory() {

        const response = await api.get(
            "/contest-history"
        );

        return response.data;
    }

};

export default contestHistoryService;