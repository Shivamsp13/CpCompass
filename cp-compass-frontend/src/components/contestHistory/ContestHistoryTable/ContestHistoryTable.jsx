import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper
} from "@mui/material";

import "./ContestHistoryTable.css";

function formatDate(date) {

    return new Date(date).toLocaleDateString(
        "en-GB",
        {
            day: "2-digit",
            month: "short",
            year: "numeric"
        }
    );

}

function getRatingColor(rating) {

    if (rating < 1200) return "#808080";      // Newbie
    if (rating < 1400) return "#008000";      // Pupil
    if (rating < 1600) return "#03A89E";      // Specialist
    if (rating < 1900) return "#0000FF";      // Expert
    if (rating < 2100) return "#AA00AA";      // Candidate Master
    if (rating < 2400) return "#FF8C00";      // Master
    return "#FF0000";                         // Grandmaster+
}

function ContestHistoryTable({ contests }) {

    return (

        <TableContainer
            component={Paper}
            className="contest-history-table"
        >

            <Table>

                <TableHead>

                    <TableRow>

                        <TableCell>#</TableCell>

                        <TableCell>Contest</TableCell>

                        <TableCell align="center">
                            Rank
                        </TableCell>

                        <TableCell>Solved</TableCell>

                        <TableCell>Date</TableCell>


                        <TableCell align="center">
                            Previous
                        </TableCell>

                        <TableCell align="center">
                            Current
                        </TableCell>

                        <TableCell align="center">
                            Δ
                        </TableCell>

                    </TableRow>

                </TableHead>

                <TableBody>

                    {

                        contests.map(

                            (contest, index) => (

                                <TableRow
                                    key={contest.cfContestId}
                                >

                                    <TableCell>

                                        {contests.length - index}

                                    </TableCell>

                                    <TableCell>

                                        <a
                                            href={contest.contestUrl}
                                            target="_blank"
                                            rel="noopener noreferrer"
                                            className="contest-link"
                                        >

                                            {contest.contestName}

                                        </a>

                                    </TableCell>

                                    <TableCell align="center">

                                        {contest.rank}

                                    </TableCell>

                                    <TableCell>

                                        {contest.solvedProblems || "—"}

                                    </TableCell>

                                    <TableCell>

                                        {formatDate(contest.contestDate)}

                                    </TableCell>

                                    <TableCell
                                        align="center"
                                        style={{
                                            color: getRatingColor(contest.previousRating),
                                            fontWeight: 700
                                        }}
                                    >

                                        {contest.previousRating}

                                    </TableCell>

                                    

                                    <TableCell
                                        align="center"
                                        style={{
                                            color: getRatingColor(contest.currentRating),
                                            fontWeight: 700
                                        }}
                                    >

                                        {contest.currentRating}

                                    </TableCell>

                                    <TableCell
                                        align="center"
                                        className={
                                            contest.ratingChange >= 0
                                                ? "rating-positive"
                                                : "rating-negative"
                                        }
                                    >

                                        {

                                            contest.ratingChange > 0

                                                ? `+${contest.ratingChange}`

                                                : contest.ratingChange

                                        }

                                    </TableCell>

                                </TableRow>

                            )

                        )

                    }

                </TableBody>

            </Table>

        </TableContainer>

    );

}

export default ContestHistoryTable;