import { useState } from "react";

import {
    FaExternalLinkAlt,
    FaEye,
    FaEyeSlash
} from "react-icons/fa";

import "./RecommendationCard.css";

function RecommendationCard({

    recommendation

}) {

    const [

        showTopics,

        setShowTopics

    ] = useState(false);

    if (!recommendation) {

        return (

            <div className="recommendation-card empty">

                <h2>

                    No Recommendation Yet

                </h2>

                <p>

                    Generate a recommendation to start practicing.

                </p>

            </div>

        );

    }

    return (

        <div className="recommendation-card">

            <p className="card-title">

                Recommended Problem

            </p>

            <h1>

                {recommendation.problemName}

            </h1>

            <div className="rating-pill">

                Rating • {recommendation.rating}

            </div>

            <div className="button-row">

                <button

                    className="topic-button"

                    onClick={() =>
                        setShowTopics(
                            !showTopics
                        )
                    }

                >

                    {

                        showTopics

                            ?

                            <>

                                <FaEyeSlash />

                                Hide Topics

                            </>

                            :

                            <>

                                <FaEye />

                                Show Topics

                            </>

                    }

                </button>

                <a

                    href={recommendation.url}

                    target="_blank"

                    rel="noreferrer"

                    className="solve-button"

                >

                    Solve on Codeforces

                    <FaExternalLinkAlt />

                </a>

            </div>

            {

                showTopics &&

                <div className="topics-container">

                    {

                        recommendation.tags.map(tag => (

                            <span

                                key={tag}

                                className="topic-chip"

                            >

                                {tag}

                            </span>

                        ))

                    }

                </div>

            }

        </div>

    );

}

export default RecommendationCard;