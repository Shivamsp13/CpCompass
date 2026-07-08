import "./RecommendationsCard.css";

function formatLastUpdated(date) {

    if (!date) {
        return "--";
    }

    const today = new Date();

    const isToday =
        date.toDateString() === today.toDateString();

    const time = date.toLocaleTimeString(
        "en-IN",
        {
            hour: "numeric",
            minute: "2-digit",
            hour12: true
        }
    );

    return isToday
        ? `Today • ${time}`
        : `${date.toLocaleDateString("en-GB")} • ${time}`;
}

function RecommendationsCard({

    recommendation,

    lastUpdated,

    onGenerateAnother,

    loading

}) {

    if (!recommendation) {

        return (

            <div className="recommendations-card">

                <h3>

                    Today's Recommendation

                </h3>

                <p>

                    Loading...

                </p>

            </div>

        );

    }
    console.log("Recommendation:", recommendation);
    console.log("Last Updated:", lastUpdated);
    return (

        <div className="recommendations-card">

            <div>

                <h3>

                    Today's Recommendation

                </h3>

                <h2>

                    {recommendation.problemName}

                </h2>

                <p className="rating">

                    Rating • {recommendation.rating}

                </p>

            </div>

            <div className="recommendation-actions">

                <a

                    href={recommendation.url}

                    target="_blank"

                    rel="noreferrer"

                    className="solve-btn"

                >

                    Solve on Codeforces

                </a>

                <button

                    className="generate-btn"

                    onClick={onGenerateAnother}

                    disabled={loading}

                >

                    {

                        loading

                            ? "Generating..."

                            : "Generate another →"

                    }

                </button>

            </div>

            <div className="recommendation-footer">

                <span>

                    Last Updated

                </span>

                <strong>

                    {formatLastUpdated(lastUpdated)}

                </strong>

            </div>

        </div>

    );

}

export default RecommendationsCard;