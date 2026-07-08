import { useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

import authService from "../../services/authService";
import { setToken } from "../../utils/auth";

import "./Login.css";

function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const [showPassword, setShowPassword] =
        useState(false);

    async function handleLogin(e) {

        e.preventDefault();

        try {

            const response =
                await authService.login({

                    email,

                    password

                });

            setToken(response.token);

            navigate("/dashboard");

        }

        catch (error) {

            alert("Invalid credentials");

        }

    }

    return (

        <div className="login-page">

            <div className="login-header">

            <h1>CP Compass</h1>

            <p>
                Competitive Programming Analytics Platform
            </p>

        </div>
        
            <form
                className="login-form"
                onSubmit={handleLogin}
            >

                {/* <h1>

                    CP Compass

                </h1>

                <p className="login-subtitle">

                    Competitive Programming Analytics Platform

                </p> */}

                <input

                    type="email"

                    placeholder="Email"

                    value={email}

                    onChange={(e) =>
                        setEmail(e.target.value)
                    }

                />

                <div className="password-field">

                    <input

                        type={
                            showPassword
                                ? "text"
                                : "password"
                        }

                        placeholder="Password"

                        value={password}

                        onChange={(e) =>
                            setPassword(e.target.value)
                        }

                    />

                    <button

                        type="button"

                        className="toggle-password"

                        onClick={() =>
                            setShowPassword(!showPassword)
                        }

                    >

                        {

                            showPassword

                                ?

                                <FaEyeSlash color="black" size={18} />

                                :

                                <FaEye color="black" size={18} />

                        }

                    </button>

                </div>

                <button
                    type="submit"
                >

                    Login

                </button>

                <div className="auth-footer">

                    <span>

                        Don't have an account?

                    </span>

                    <button

                        type="button"

                        className="link-button"

                        onClick={() =>
                            navigate("/register")
                        }

                    >

                        Register

                    </button>

                </div>

            </form>

        </div>

    );

}

export default Login;