import { Routes, Route, Navigate } from "react-router-dom";

import MainLayout from "./layouts/MainLayout/MainLayout";

import Dashboard from "./pages/Dashboard/Dashboard";
import Recommendations from "./pages/Recommendations/Recommendations";
import TopicInsights from "./pages/TopicInsights/TopicInsights";
import ProgressReview from "./pages/ProgressReview/ProgressReview";
import ContestHistory from "./pages/ContestHistory/ContestHistory";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {

    return (

        <Routes>

            <Route
                path="/"
                element={<Navigate to="/login" replace />}
            />

            <Route
                path="/login"
                element={<Login />}
            />

            <Route
                path="/register"
                element={<Register />}
            />

            <Route
                path="/dashboard"
                element={
                    <ProtectedRoute>

                        <MainLayout>

                            <Dashboard />

                        </MainLayout>

                    </ProtectedRoute>
                }
            />

            <Route
                path="/recommendations"
                element={
                    <ProtectedRoute>

                        <MainLayout>

                            <Recommendations />

                        </MainLayout>

                    </ProtectedRoute>
                }
            />

            <Route
                path="/topic-insights"
                element={
                    <ProtectedRoute>

                        <MainLayout>

                            <TopicInsights />

                        </MainLayout>

                    </ProtectedRoute>
                }
            />

            <Route
                path="/progress-review"
                element={
                    <ProtectedRoute>

                        <MainLayout>

                            <ProgressReview />

                        </MainLayout>

                    </ProtectedRoute>
                }
            />

            <Route
                path="/contest-history"
                element={
                    <ProtectedRoute>

                        <MainLayout>

                            <ContestHistory />

                        </MainLayout>

                    </ProtectedRoute>
                }
            />

            <Route
                path="*"
                element={<Navigate to="/login" replace />}
            />

        </Routes>

    );

}

export default App;