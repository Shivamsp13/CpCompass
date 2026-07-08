import { useEffect, useState } from "react";

import Loader from "../../components/common/Loader/Loader";

import ContestHistoryTable
    from "../../components/contestHistory/ContestHistoryTable/ContestHistoryTable";

import contestHistoryService
    from "../../services/contestHistoryService";

import "./ContestHistory.css";

function ContestHistory() {

    const [loading, setLoading] = useState(true);

    const [contests, setContests] = useState([]);

    useEffect(() => {

        fetchContestHistory();

    }, []);

    async function fetchContestHistory() {

        try {

            const response =
                await contestHistoryService
                    .getContestHistory();

            setContests(response);

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

    return (

        <div className="contest-history-page">

            <div className="contest-history-header">

                <p
                    className="page-subtitle"
                    style={{ fontSize: "18px" }}
                >

                    View all your rated Codeforces contests,
                    problems solved, contest dates, and rating
                    changes throughout your competitive
                    programming journey.

                </p>

            </div>

            <ContestHistoryTable

                contests={contests}

            />

        </div>

    );

}

export default ContestHistory;