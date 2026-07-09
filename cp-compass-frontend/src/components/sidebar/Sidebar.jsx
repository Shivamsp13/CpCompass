import { useState } from "react";

import syncService from "../../services/syncService";

import CircularProgress from "@mui/material/CircularProgress";

import Snackbar from "@mui/material/Snackbar";

import Alert from "@mui/material/Alert";

import { NavLink } from "react-router-dom";

import {
    FaHome,
    FaBullseye,
    FaBook,
    FaStar,
    FaHistory,
    FaSync,
    FaSignOutAlt,
    FaClock
} from "react-icons/fa";

import { useNavigate } from "react-router-dom";

import "./Sidebar.css";

function Sidebar() {

    const [syncing, setSyncing] = useState(false);

    const [snackbar, setSnackbar] = useState({

        open: false,

        severity: "success",

        message: ""

    });

    const navigate = useNavigate();

    const [lastSync, setLastSync] = useState(
        localStorage.getItem("lastSync")
    );

    async function handleSync() {

        if (syncing) {

            return;

        }

        try {

            setSyncing(true);

            await syncService.syncCodeforces();

            const now = new Date().toLocaleString(
                "en-IN",
                {
                    day: "2-digit",
                    month: "short",
                    year: "numeric",
                    hour: "numeric",
                    minute: "2-digit",
                    hour12: true
                }
            );

            localStorage.setItem("lastSync", now);

            setLastSync(now);

            setSnackbar({

                open: true,

                severity: "success",

                message: "Codeforces data synced successfully."

            });

            setTimeout(() => {

                window.location.reload();

            }, 1200);

        }

        catch (error) {

            setSnackbar({

                open: true,

                severity: "error",

                message:
                    error.response?.data ||
                    "Failed to sync Codeforces data."

            });

        }

        finally {

            setSyncing(false);

        }

    }

    function handleLogout() {

        localStorage.clear();

        navigate("/login");

    }

    return (

        <aside className="sidebar">

            <h2 className="logo">

                CP Compass

            </h2>

            <nav className="sidebar-nav">

                <NavLink
                    to="/dashboard"
                    className="nav-item"
                >
                    <FaHome />
                    <span>Dashboard</span>
                </NavLink>

                <NavLink
                    to="/recommendations"
                    className="nav-item"
                >
                    <FaBullseye />
                    <span>Recommendations</span>
                </NavLink>

                <NavLink
                    to="/contest-history"
                    className="nav-item"
                >
                    <FaHistory />
                    <span>Contest History</span>
                </NavLink>

                <NavLink
                    to="/topic-insights"
                    className="nav-item"
                >
                    <FaBook />
                    <span>Topic Insights</span>
                </NavLink>

                <NavLink
                    to="/progress-review"
                    className="nav-item"
                >
                    <FaStar />
                    <span>Progress Review</span>
                </NavLink>


                <hr className="sidebar-divider" />

                <button

                    className="sync-button"

                    onClick={handleSync}

                    disabled={syncing}

                >

                    {

                        syncing

                            ?

                            <CircularProgress

                                size={18}

                                color="inherit"

                            />

                            :

                            <FaSync />

                    }

                    <span>

                        {

                            syncing

                                ?

                                "Syncing..."

                                :

                                "Sync Data"

                        }

                    </span>

                </button>

                <div className="last-sync-card">

                    <div className="last-sync-title">

                        <FaClock />

                        <span>

                            Last Sync

                        </span>

                    </div>

                    <div className="last-sync-time">

                        {

                            lastSync

                                ?

                                lastSync

                                :

                                "Never"

                        }

                    </div>

                </div>

                <button

                    className="logout-button"

                    onClick={handleLogout}

                >

                    <FaSignOutAlt />

                    <span>

                        Logout

                    </span>

                </button>

                <Snackbar

                    open={snackbar.open}

                    autoHideDuration={3000}

                    onClose={() =>

                        setSnackbar({

                            ...snackbar,

                            open: false

                        })

                    }

                >

                    <Alert

                        severity={snackbar.severity}

                        variant="filled"

                    >

                        {snackbar.message}

                    </Alert>

                </Snackbar>

            </nav>

        </aside>

    );

}

export default Sidebar;