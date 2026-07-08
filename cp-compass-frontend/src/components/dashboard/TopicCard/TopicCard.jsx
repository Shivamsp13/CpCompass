import "./TopicCard.css";

function TopicCard({

    title,

    topics,

    type

}) {

    return (

        <div className="topic-card">

            <h3>

                {title}

            </h3>

            <div className="topic-list">

                {

                    topics.map(

                        topic => (

                            <span

                                key={topic}

                                className={`topic-chip ${type}`}

                            >

                                {topic}

                            </span>

                        )

                    )

                }

            </div>

        </div>

    );

}

export default TopicCard;