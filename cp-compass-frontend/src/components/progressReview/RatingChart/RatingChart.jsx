import {
    ResponsiveContainer,
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    CartesianGrid,
    Cell
} from "recharts";
import "./RatingChart.css";

function getBarColor(rating) {
    if (rating < 1200) return "#BDBDBD";     // Gray
    if (rating < 1400) return "#77FF77";     // Green
    if (rating < 1600) return "#77DDBB";     // Cyan
    if (rating < 1900) return "#AAAAFF";     // Blue
    if (rating < 2100) return "#FF88FF";     // Purple
    if (rating < 2400) return "#FFD700";     // Yellow
    return "#FF7777";                        // Red
}

function CustomTooltip({ active, payload, label }) {
    if (!active || !payload?.length) {
        return null;
    }

    return (
        <div className="rating-tooltip">
            <h4>{label}</h4>
            <div className="tooltip-content">
                <span>Solved Problems</span>
                <strong>{payload[0].value}</strong>
            </div>
        </div>
    );
}

function RatingChart({ data }) {
    return (
        <div className="rating-chart-card">
            <div className="rating-chart-header">
                <h2>Problems Solved by Rating</h2>
                <p>Distribution of solved problems across rating levels</p>
            </div>

            <ResponsiveContainer width="100%" height={480}>
                <BarChart
                    data={data}
                    barCategoryGap="10%"
                    barGap={2}
                    margin={{ top: 20, right: 20, left: 0, bottom: 10 }}
                >
                    <CartesianGrid
                        strokeDasharray="3 3"
                        stroke="#374151"
                        vertical={false}
                    />
                    <XAxis
                        dataKey="rating"
                        tick={{ fill: "#d1d5db", fontSize: 12 }}
                        tickLine={false}
                        axisLine={false}
                    />
                    <YAxis
                        allowDecimals={false}
                        tick={{ fill: "#d1d5db", fontSize: 12 }}
                        tickLine={false}
                        axisLine={false}
                    />
                    <Tooltip
                        content={<CustomTooltip />}
                        cursor={{ fill: "rgba(255,255,255,.04)" }}
                    />
                    <Bar dataKey="solved" radius={[6, 6, 0, 0]}>
                        {data.map(item => (
                            <Cell key={item.rating} fill={getBarColor(item.rating)} />
                        ))}
                    </Bar>
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
}

export default RatingChart;