import { useState } from "react";
import Select from "react-select";
import Slider from "@mui/material/Slider";
import { FaCheckCircle } from "react-icons/fa";

import "./RecommendationFilters.css";

const topicOptions = [
    { value: "implementation", label: "Implementation" },
    { value: "math", label: "Math" },
    { value: "greedy", label: "Greedy" },
    { value: "dp", label: "Dynamic Programming" },
    { value: "data structures", label: "Data Structures" },
    { value: "brute force", label: "Brute Force" },
    { value: "constructive algorithms", label: "Constructive Algorithms" },
    { value: "graphs", label: "Graphs" },
    { value: "sortings", label: "Sorting" },
    { value: "binary search", label: "Binary Search" },
    { value: "dfs and similar", label: "DFS" },
    { value: "trees", label: "Trees" },
    { value: "strings", label: "Strings" },
    { value: "number theory", label: "Number Theory" },
    { value: "two pointers", label: "Two Pointers" },
    { value: "bitmasks", label: "Bitmasks" }
];

function RecommendationFilters({ onGenerate }) {

    const [recommendationMode, setRecommendationMode] =
        useState("smart");

    const [ratingRange, setRatingRange] =
        useState([800, 3500]);

    const [selectedTopics, setSelectedTopics] =
        useState([]);

    function handleSubmit(event) {

        event.preventDefault();

        onGenerate({

            minRating:
                recommendationMode === "smart"
                    ? null
                    : ratingRange[0],

            maxRating:
                recommendationMode === "smart"
                    ? null
                    : ratingRange[1],

            topics:
                recommendationMode === "smart"
                    ? []
                    : selectedTopics.map(
                        topic => topic.value
                    )

        });

    }

    return (

        <form
            className="recommendation-filters"
            onSubmit={handleSubmit}
        >

            <h2>

                Problem Recommendations

            </h2>

            <div className="filters-row">

                <div className="filter-group">

                    <label>

                        Recommendation Mode

                    </label>

                    <div className="mode-options">

                        <label>

                            <input
                                type="radio"
                                checked={
                                    recommendationMode === "smart"
                                }
                                onChange={() =>
                                    setRecommendationMode("smart")
                                }
                            />

                            Smart Recommendation

                        </label>

                        <label>

                            <input
                                type="radio"
                                checked={
                                    recommendationMode === "custom"
                                }
                                onChange={() =>
                                    setRecommendationMode("custom")
                                }
                            />

                            Custom Recommendation

                        </label>

                    </div>

                </div>

                <div className="filter-group">

                    {

                        recommendationMode === "smart"

                            ?

                            <>

                                <label>

                                    Smart Recommendation

                                </label>

                                <div className="smart-message">

                                    <p>
                                        <FaCheckCircle className="check-icon" />
                                        Uses your Growth Zone
                                    </p>

                                    <p>
                                        <FaCheckCircle className="check-icon" />
                                        Focuses on Weak Topics
                                    </p>

                                    <p>
                                        <FaCheckCircle className="check-icon" />
                                        Avoids Solved Problems
                                    </p>

                                    <p>
                                        <FaCheckCircle className="check-icon" />
                                        Picks the Best Practice Problem
                                    </p>

                                </div>

                            </>

                            :

                            <>

                                <label>

                                    Practice Rating

                                </label>

                                <Slider

                                    value={ratingRange}

                                    onChange={(e, value) =>
                                        setRatingRange(value)
                                    }

                                    min={800}

                                    max={3500}

                                    step={100}

                                    disableSwap

                                    valueLabelDisplay="auto"

                                />

                                <div className="slider-values">

                                    <span>

                                        {ratingRange[0]}

                                    </span>

                                    <span>

                                        -

                                    </span>

                                    <span>

                                        {ratingRange[1]}

                                    </span>

                                </div>

                            </>

                    }

                </div>

                {

                    recommendationMode === "custom"

                    &&

                    <div className="filter-group">

                        <label>

                            Topics (Optional)

                        </label>

                        <Select

                            isMulti

                            options={topicOptions}

                            value={selectedTopics}

                            onChange={setSelectedTopics}

                            closeMenuOnSelect={false}

                            placeholder="Select Topics..."

                            classNamePrefix="select"

                        />

                    </div>

                }

            </div>

            <button type="submit">

                Generate Recommendation

            </button>

        </form>

    );

}

export default RecommendationFilters;