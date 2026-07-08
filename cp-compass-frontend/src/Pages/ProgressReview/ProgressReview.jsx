import { useEffect, useState } from "react";

import {
    ToggleButton,
    ToggleButtonGroup,
    TextField,
    Button
} from "@mui/material";

import Loader from "../../components/common/Loader/Loader";

import SummaryCards
    from "../../components/progressReview/SummaryCards/SummaryCards";

import RatingChart
    from "../../components/progressReview/RatingChart/RatingChart";

// import StreakCards
//     from "../../components/progressReview/StreakCards/StreakCards";

import progressReviewService
    from "../../services/progressReviewService";

import "./ProgressReview.css";

function ProgressReview() {

    const [loading, setLoading] = useState(true);

    const [days, setDays] = useState(30);

    const [customDays, setCustomDays] = useState("");

    const [activity, setActivity] = useState(null);

    useEffect(() => {

        fetchProgressReview(days);

    }, [days]);

    async function fetchProgressReview(selectedDays) {

        setLoading(true);

        try {

            const response =
                await progressReviewService
                    .getProgressReview(selectedDays);

            setActivity(
                response.activitySummary
            );

        }

        catch (error) {

            console.error(error);

        }

        finally {

            setLoading(false);

        }

    }

    const handleDaysChange = (

        event,

        newValue

    ) => {

        if (newValue !== null) {

            setDays(newValue);

            setCustomDays("");

        }

    };

    const handleCustomDays = () => {

        const value = Number(customDays);

        if (!value || value <= 0) {

            return;

        }

        setDays(value);

    };

    if (loading || !activity) {

        return <Loader />;

    }

    return (

        <div className="progress-review-page">

            <div className="progress-review-header">

                <p className="page-subtitle" style={{ fontSize: "18px" }}>
                    Review your coding progress over a selected time period with insights into solved problems, contests, rating changes, and problem difficulty.
                </p>

            </div>

            <div className="progress-filter-section">

                <ToggleButtonGroup

                    value={

                        [7, 30, 90].includes(days)

                            ? days

                            : null

                    }

                    exclusive

                    onChange={handleDaysChange}

                >

                    <ToggleButton
                        value={7}
                        sx={{
                            color: "#ffffff !important",
                            fontWeight: "700 !important",
                            backgroundColor: "#242d3b",
                            textTransform: "none",
                            "&:hover": {
                                backgroundColor: "#2f3b4f",
                            },
                            "&.Mui-selected": {
                                color: "#ffffff !important",
                                backgroundColor: "#2563eb !important",
                            },
                        }}
                    >
                        7 Days
                    </ToggleButton>

                    <ToggleButton
                        value={30}
                        sx={{
                            color: "#ffffff !important",
                            fontWeight: "700 !important",
                            backgroundColor: "#242d3b",
                            textTransform: "none",
                            "&:hover": {
                                backgroundColor: "#2f3b4f",
                            },
                            "&.Mui-selected": {
                                color: "#ffffff !important",
                                backgroundColor: "#2563eb !important",
                            },
                        }}
                    >
                        30 Days
                    </ToggleButton>

                    <ToggleButton
                        value={90}
                        sx={{
                            color: "#ffffff !important",
                            fontWeight: "700 !important",
                            backgroundColor: "#242d3b",
                            textTransform: "none",
                            "&:hover": {
                                backgroundColor: "#2f3b4f",
                            },
                            "&.Mui-selected": {
                                color: "#ffffff !important",
                                backgroundColor: "#2563eb !important",
                            },
                        }}
                    >
                        90 Days
                    </ToggleButton>

                </ToggleButtonGroup>

                <div className="custom-days">

                    <TextField

                        label="Custom Days"

                        size="small"

                        type="number"

                        value={customDays}

                        onChange={(e) =>

                            setCustomDays(e.target.value)

                        }

                        inputProps={{

                            min: 1

                        }}

                    />

                    <Button

                        variant="contained"

                        onClick={handleCustomDays}

                    >

                        Apply

                    </Button>

                </div>

            </div>

            <SummaryCards

                activity={activity}

            />

            <RatingChart

                data={activity.ratingDistribution}

            />

            {/* <StreakCards

                currentStreak={activity.currentStreak}

                longestStreak={activity.longestStreak}

            /> */}

        </div>

    );

}

export default ProgressReview;