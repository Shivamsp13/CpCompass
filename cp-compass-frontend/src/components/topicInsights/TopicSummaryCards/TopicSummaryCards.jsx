import "./TopicSummaryCards.css";

function TopicSummaryCards({

    strongTopics,

    weakTopics

}) {

    return (

        <div className="summary-grid">

            <div className="summary-card">

                <h2>

                    Strong Topics

                </h2>

                {

                    strongTopics.map(topic => (

                        <div

                            key={topic.topic}

                            className="summary-item"

                        >

                            <div className="summary-left">

                                <span className="summary-icon good-icon" />

                                <span>

                                    {topic.topic}

                                </span>

                            </div>

                            <span className="good">

                                {topic.acceptanceRate.toFixed(1)}%

                            </span>

                        </div>

                    ))

                }

            </div>

            <div className="summary-card">

                <h2>

                    Weak Topics

                </h2>

                {

                    weakTopics.map(topic => (

                        <div

                            key={topic.topic}

                            className="summary-item"

                        >

                            <div className="summary-left">

                                <span className="summary-icon bad-icon" />

                                <span>

                                    {topic.topic}

                                </span>

                            </div>

                            <span className="bad">

                                {topic.acceptanceRate.toFixed(1)}%

                            </span>

                        </div>

                    ))

                }

            </div>

        </div>

    );

}

export default TopicSummaryCards;