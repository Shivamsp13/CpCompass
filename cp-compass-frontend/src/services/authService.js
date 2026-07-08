import api from "./api";

const authService = {

    async login(credentials) {

        const response =
            await api.post(
                "/auth/login",
                credentials
            );

        return response.data;

    },

    async register(data) {

        const response =
            await api.post(
                "/auth/register",
                data
            );

        return response.data;

    }

};

export default authService;