import Sidebar from "../../components/Sidebar/Sidebar";
import Navbar from "../../components/Navbar/Navbar";

import "./MainLayout.css";

function MainLayout({ children }) {

    return (

        <div className="main-layout">

            <Sidebar />

            <div className="main-content">

                <Navbar />

                <div className="page-content">

                    {children}

                </div>

            </div>

        </div>

    );

}

export default MainLayout;