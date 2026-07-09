import { useEffect, useState } from "react";

import Loader from "../../components/common/Loader/Loader";

import TopicSummaryCards
    from "../../components/topicInsights/TopicSummaryCards/TopicSummaryCards";

import TopicCard
    from "../../components/topicInsights/TopicCard/TopicCard";

import topicInsightsService
    from "../../services/topicInsightsService";

import "./TopicInsights.css";

function TopicInsights() {

    const [

        topics,

        setTopics

    ] = useState([]);

    const [

        loading,

        setLoading

    ] = useState(true);

    useEffect(() => {

        loadTopicInsights();

    }, []);

    async function loadTopicInsights() {

        try {

            const response =
                await topicInsightsService
                    .getTopicInsights();

            setTopics(
                response.topics
            );

        }

        catch (error) {

            console.error(error);

        }

        finally {

            setLoading(false);

        }

    }

    if (loading) {

        return <Loader />;

    }

    const strongTopics =
        [...topics]

            .sort(

                (a, b) =>

                    b.acceptanceRate
                    - a.acceptanceRate

            )

            .slice(0, 3);

    const weakTopics =
        [...topics]

            .sort(

                (a, b) =>

                    a.acceptanceRate
                    - b.acceptanceRate

            )

            .slice(0, 3);

    return (

        <div className="topic-insights-page">

            {/* <h1>Topic Insights</h1> */}

            <p className="page-subtitle">
                View topic-wise analytics to identify your strengths, weaknesses, and areas for improvement based on your Codeforces submissions.          </p>

            <TopicSummaryCards

                strongTopics={strongTopics}

                weakTopics={weakTopics}

            />

            <div className="topic-list">

                {

                    topics.map(topic => (

                        <TopicCard
                            key={topic.topic}
                            topic={topic}
                        />

                    ))

                }

            </div>

        </div>

    );

}

export default TopicInsights;