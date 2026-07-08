import api from "./api";

const recommendationsService = {

    // Used by the Recommendations page
    generateRecommendation: async (request) => {

        const response =
            await api.post(
                "/recommendations/generate",
                request
            );

        return response.data;

    },

    // Used by the Dashboard
    getTodayRecommendation: async () => {

        const response =
            await api.get(
                "/recommendations/today"
            );

        return response.data;

    },

    // Used by the "Generate another" button
    generateAnotherRecommendation: async () => {

        const response =
            await api.post(
                "/recommendations/generate-another"
            );

        return response.data;

    }

};

export default recommendationsService;