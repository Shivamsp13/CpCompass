import { useLocation } from "react-router-dom";

import "./Navbar.css";

function Navbar() {

    const location = useLocation();

    const pageTitles = {
        "/dashboard": "Dashboard",
        "/recommendations": "Recommendations",
        "/topic-insights": "Topic Insights",
        "/rating-insights": "Rating Insights",
        "/progress-review": "Progress Review",
        "/journal": "Journal",
        "/profile": "Profile"
    };

    const pageTitle =
        pageTitles[location.pathname] || "CP Compass";

    return (

        <header className="navbar">

            <h1 className="page-title">

                {pageTitle}

            </h1>

            <div className="user-section">

                👤 Shivam

            </div>

        </header>

    );

}

export default Navbar;