import { useEffect, useState } from "react";

import {
    FaTrophy,
    FaChartLine,
    FaBullseye,
    FaFlagCheckered
} from "react-icons/fa";

import Loader from "../../components/common/Loader/Loader";
import CurrentFocusCard
    from "../../components/dashboard/CurrentFocusCard/CurrentFocusCard";
import StatCard from "../../components/dashboard/StatCard/StatCard";
import RecommendationsCard from "../../components/dashboard/RecommendationsCard/RecommendationsCard";
import ProgressCard from "../../components/dashboard/ProgressCard/ProgressCard";
import TopicCard from "../../components/dashboard/TopicCard/TopicCard";

import { getDashboard } from "../../services/dashboardService";
import recommendationsService from "../../services/recommendationsService";

import "./Dashboard.css";

function Dashboard() {

    const [dashboard, setDashboard] =
        useState(null);

    const [recommendation, setRecommendation] =
        useState(null);

    const [loadingRecommendation, setLoadingRecommendation] =
        useState(false);

    const [lastUpdated, setLastUpdated] = useState(null);

    async function loadDashboard() {

        try {

            const data =
                await getDashboard();

            setDashboard(data);

        }

        catch (error) {

            console.error(error);

        }

    }

    async function loadRecommendation() {

        try {

            setLoadingRecommendation(true);

            const data =
                await recommendationsService
                    .getTodayRecommendation();

            setRecommendation(data);
            setRecommendation(data);

            const recommendationKey =
                `${data.contestId}-${data.problemIndex}`;

            const storedKey =
                localStorage.getItem("recommendationKey");

            const storedTime =
                localStorage.getItem("recommendationTime");

            if (storedKey === recommendationKey && storedTime) {

                setLastUpdated(
                    new Date(storedTime)
                );

            }

            else {

                const now = new Date();

                localStorage.setItem(
                    "recommendationKey",
                    recommendationKey
                );

                localStorage.setItem(
                    "recommendationTime",
                    now.toISOString()
                );

                setLastUpdated(now);

            }

        }

        catch (error) {

            console.error(error);

        }

        finally {

            setLoadingRecommendation(false);

        }

    }

    async function generateAnotherRecommendation() {

        try {

            setLoadingRecommendation(true);

            const data =
                await recommendationsService
                    .generateAnotherRecommendation();

            setRecommendation(data);
            setRecommendation(data);

            const recommendationKey =
                `${data.contestId}-${data.problemIndex}`;

            const storedKey =
                localStorage.getItem("recommendationKey");

            const storedTime =
                localStorage.getItem("recommendationTime");

            if (storedKey === recommendationKey && storedTime) {

                setLastUpdated(
                    new Date(storedTime)
                );

            }

            else {

                const now = new Date();

                localStorage.setItem(
                    "recommendationKey",
                    recommendationKey
                );

                localStorage.setItem(
                    "recommendationTime",
                    now.toISOString()
                );

                setLastUpdated(now);

            }

        }

        catch (error) {

            console.error(error);

        }

        finally {

            setLoadingRecommendation(false);

        }

    }

    useEffect(() => {

        loadDashboard();

        loadRecommendation();

    }, []);

    if (!dashboard) {

        return <Loader />;

    }

    return (

        <div className="dashboard">

            <section className="stats-grid">

                <StatCard
                    icon={<FaTrophy />}
                    title="Current Rating"
                    value={dashboard.currentRating}
                />

                <StatCard
                    icon={<FaChartLine />}
                    title="Max Rating"
                    value={dashboard.maxRating}
                />

                <StatCard
                    icon={<FaBullseye />}
                    title="Growth Zone"
                    value={dashboard.growthZone}
                />

                <StatCard
                    icon={<FaFlagCheckered />}
                    title="Contests"
                    value={dashboard.totalContests}
                />

            </section>

            <section className="dashboard-grid">

                <div className="recommendation-area">

                    <RecommendationsCard
                        recommendation={recommendation}
                        lastUpdated={lastUpdated}
                        onGenerateAnother={generateAnotherRecommendation}
                        loading={loadingRecommendation}
                    />

                </div>

                <ProgressCard

                    dashboard={dashboard}

                />

            </section>

            <section className="topics-grid">

                <TopicCard

                    title="Strong Topics"

                    topics={dashboard.strongTopics}

                    type="strong"

                />

                <TopicCard

                    title="Weak Topics"

                    topics={dashboard.weakTopics}

                    type="weak"

                />

            </section>

            <section className="current-focus-section">

                <CurrentFocusCard

                    dashboard={dashboard}

                />

            </section>

        </div>

    );

}

export default Dashboard;