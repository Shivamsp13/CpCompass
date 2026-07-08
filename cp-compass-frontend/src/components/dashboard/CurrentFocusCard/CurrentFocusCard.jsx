import {
    FaBullseye,
    FaExclamationTriangle,
    FaCalendarAlt
} from "react-icons/fa";

import "./CurrentFocusCard.css";

function CurrentFocusCard({ dashboard }) {

    return (

        <section className="current-focus-card">

            {/* <h2 className="current-focus-title">

                Current Focus

            </h2> */}

            <div className="current-focus-content">

                <div className="focus-item">

                    <div className="focus-icon">

                        <FaBullseye />

                    </div>

                    <p className="focus-label">

                        Practice Rating

                    </p>

                    <h3 className="focus-value">

                        {dashboard.growthZone}

                    </h3>

                </div>

                <div className="focus-divider"></div>

                <div className="focus-item">

                    <div className="focus-icon warning">

                        <FaExclamationTriangle />

                    </div>

                    <p className="focus-label">

                        Topic to Improve

                    </p>

                    <h3 className="focus-value">

                        {dashboard.weakTopics[0]}

                    </h3>

                </div>

                <div className="focus-divider"></div>

                <div className="focus-item">

                    <div className="focus-icon success">

                        <FaCalendarAlt />

                    </div>

                    <p className="focus-label">

                        Last 30 Days

                    </p>

                    <h3 className="focus-value">

                        {dashboard.last30DaysSolved} Solved

                    </h3>

                </div>

            </div>

        </section>

    );

}

export default CurrentFocusCard;