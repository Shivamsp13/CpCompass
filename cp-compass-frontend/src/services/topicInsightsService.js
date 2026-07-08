import api from "./api";

const topicInsightsService = {

    async getTopicInsights() {

        const response = await api.get(
            "/topic-insights"
        );

        return response.data;

    }

};

export default topicInsightsService;