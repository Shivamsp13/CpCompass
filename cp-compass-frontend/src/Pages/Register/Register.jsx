import { useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

import authService from "../../services/authService";

import "../Login/Login.css";

function Register() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const [codeforcesHandle, setCodeforcesHandle] =
        useState("");

    const [showPassword, setShowPassword] =
        useState(false);

    async function handleRegister(e) {

        e.preventDefault();

        try {

            await authService.register({

                email,

                password,

                codeforcesHandle

            });

            alert("Registration successful!");

            navigate("/login");

        }

        catch (error) {

            alert(
                error.response?.data ||
                "Registration failed."
            );

        }

    }

    return (

        <div className="login-page">

            <div className="login-header">

                <h1>

                    CP Compass

                </h1>

                <p>

                    Competitive Programming Analytics Platform

                </p>

            </div>

            <form
                className="login-form"
                onSubmit={handleRegister}
                autoComplete="off"
            >

                <input

                    type="email"
                    autoComplete="off"
                    placeholder="Email"

                    value={email}

                    onChange={(e) =>
                        setEmail(e.target.value)
                    }

                />

                <input

                    type="text"
                    autoComplete="off"
                    placeholder="Codeforces Handle"

                    value={codeforcesHandle}

                    onChange={(e) =>
                        setCodeforcesHandle(
                            e.target.value
                        )
                    }

                />

                <div className="password-field">

                    <input

                        type={
                            showPassword
                                ? "text"
                                : "password"
                        }
                        autoComplete="new-password"
                        placeholder="Password"

                        value={password}

                        onChange={(e) =>
                            setPassword(
                                e.target.value
                            )
                        }

                    />

                    <button

                        type="button"

                        className="toggle-password"

                        onClick={() =>
                            setShowPassword(
                                !showPassword
                            )
                        }

                    >

                        {

                            showPassword

                                ?

                                <FaEyeSlash
                                    color="black"
                                    size={18}
                                />

                                :

                                <FaEye
                                    color="black"
                                    size={18}
                                />

                        }

                    </button>

                </div>

                <button
                    type="submit"
                >

                    Register

                </button>

                <div className="auth-footer">

                    <span>

                        Already have an account?

                    </span>

                    <button

                        type="button"

                        className="link-button"

                        onClick={() =>
                            navigate("/login")
                        }

                    >

                        Login

                    </button>

                </div>

            </form>

        </div>

    );

}

export default Register;