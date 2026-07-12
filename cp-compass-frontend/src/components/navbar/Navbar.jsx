import { useLocation } from "react-router-dom";

import { useEffect, useState } from "react";
import { getCurrentUser } from "../../services/userService";
import "./Navbar.css";

function Navbar() {

    const [user, setUser] = useState(null);

    useEffect(() => {

        const fetchUser = async () => {

            try {

                const data = await getCurrentUser();

                setUser(data);

            } catch (error) {

                console.error(error);

            }

        };

        fetchUser();

    }, []);

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

                👤 {user?.codeforcesHandle}

            </div>

        </header>

    );

}

export default Navbar;