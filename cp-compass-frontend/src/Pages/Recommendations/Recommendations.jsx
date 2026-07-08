import { useState } from "react";

import Loader from "../../components/common/Loader/Loader";

import RecommendationFilters
    from "../../components/recommendations/RecommendationFilters/RecommendationFilters";

import RecommendationCard
    from "../../components/recommendations/RecommendationCard/RecommendationCard";

import recommendationsService
    from "../../services/recommendationsService";

import "./Recommendations.css";

function Recommendations() {

    const [

        recommendation,

        setRecommendation

    ] = useState(null);

    const [

        loading,

        setLoading

    ] = useState(false);

    

    async function handleGenerate(
        request
    ) {

        try {

            setLoading(true);

            const response =
                await recommendationsService
                    .generateRecommendation(
                        request
                    );

            setRecommendation(
                response
            );

        }

        catch (error) {

            console.error(error);

        }

        finally {

            setLoading(false);

        }

    }

    return (

        <div className="recommendations-page">

            <div className="page-header">

                <p className="page-subtitle">

                    Smart Recommendation analyzes your solved problems, weak topics, and growth rating range to recommend the most relevant unsolved Codeforces problem.
                </p>

            </div>

            <RecommendationFilters

                onGenerate={handleGenerate}

            />

            <div className="recommendation-result">

                {

                    loading

                        ?

                        <Loader />

                        :

                        <RecommendationCard

                            recommendation={
                                recommendation
                            }

                        />

                }

            </div>

        </div>

    );

}

export default Recommendations;