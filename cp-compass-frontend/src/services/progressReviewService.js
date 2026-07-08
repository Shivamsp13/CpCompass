import api from "./api";

const progressReviewService = {

    async getProgressReview(days = 30) {

        const response = await api.get(
            "/progress-review",
            {
                params: {
                    days
                }
            }
        );

        return response.data;
    }

};

export default progressReviewService;