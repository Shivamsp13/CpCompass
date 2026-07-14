import "./ProgressCard.css";

function ProgressCard({

    dashboard

}){

    return(

        <div className="progress-card">

            <h3>

                Progress Summary

            </h3>

            <div className="progress-row">

                <span>

                    Accepted Solutions

                </span>

                <strong>

                    {dashboard.totalSolved}

                </strong>

            </div>

            <div className="progress-row">

                <span>

                    Total Submissions

                </span>

                <strong>

                    {dashboard.totalSubmissions}

                </strong>

            </div>

            <div className="progress-row">

                <span>

                    Contests Given

                </span>

                <strong>

                    {dashboard.totalContests}

                </strong>

            </div>

            <div className="progress-row">

                <span>

                    Growth Zone

                </span>

                <strong>

                    {dashboard.growthZone}

                </strong>

            </div>

        </div>

    );

}

export default ProgressCard;