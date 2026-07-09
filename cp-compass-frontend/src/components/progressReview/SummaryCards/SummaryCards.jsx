import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import TrendingDownIcon from "@mui/icons-material/TrendingDown";
import SportsScoreIcon from "@mui/icons-material/SportsScore";
import EmojiEventsIcon from "@mui/icons-material/EmojiEvents";

import "./SummaryCards.css";

function SummaryCards({ activity }) {

    const ratingPositive = activity.ratingChange >= 0;

    return (

        <div className="summary-cards">

            <div className="summary-card">

                <div className="summary-icon">

                    <SportsScoreIcon />

                </div>

                <span className="summary-title">

                    Problems Solved

                </span>

                <h2>

                    {activity.problemsSolved}

                </h2>

            </div>

            <div className="summary-card">

                <div className="summary-icon">

                    <EmojiEventsIcon />

                </div>

                <span className="summary-title">

                    Contests Given

                </span>

                <h2>

                    {activity.contestsParticipated}

                </h2>

            </div>

            <div className="summary-card">

                <div className="summary-icon">

                    {

                        ratingPositive ?

                            <TrendingUpIcon />

                            :

                            <TrendingDownIcon />

                    }

                </div>

                <span className="summary-title">

                    Rating Change

                </span>

                <h2
                    className={
                        ratingPositive
                            ?
                            "positive-rating"
                            :
                            "negative-rating"
                    }
                >

                    {

                        ratingPositive

                            ?

                            `+${activity.ratingChange}`

                            :

                            activity.ratingChange

                    }

                </h2>

            </div>

        </div>

    );

}

export default SummaryCards;