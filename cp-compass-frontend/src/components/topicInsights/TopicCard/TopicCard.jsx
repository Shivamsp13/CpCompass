import "./TopicCard.css";

function TopicCard({

    topic

}) {

    return (

        <div className="topic-card">

            <div className="topic-header">

                <h2>

                    {topic.topic}

                </h2>

                <span className="topic-percentage">

                    {topic.acceptanceRate.toFixed(1)}%

                </span>

            </div>

            <div className="progress-bar">

                <div

                    className="progress-fill"

                    style={{

                        width: `${topic.acceptanceRate}%`

                    }}

                />

            </div>

            <div className="topic-stats">

                <div className="stat">

                    <span className="stat-label">

                        Accepted

                    </span>

                    <span className="stat-value">

                        {topic.accepted}

                    </span>

                </div>

                <div className="stat">

                    <span className="stat-label">

                        Attempts

                    </span>

                    <span className="stat-value">

                        {topic.attempts}

                    </span>

                </div>

                <div className="stat">

                    <span className="stat-label">

                        Success Rate

                    </span>

                    <span className="stat-value">

                        {topic.acceptanceRate.toFixed(1)}%

                    </span>

                </div>

            </div>

        </div>

    );

}

export default TopicCard;