import logoLeft from "../assets/images/registerLeft.png";
import { RegisterForm } from "../components/forms/RegisterForm";

function Register() {
  return (
    <div className="registerPage w-screen grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-2 pb-5 px-16">
      <div className="registerLeft flex justify-center items-center">
        <img src={logoLeft} className="logo w-auto" alt="" />
      </div>
      <div className="registerRight px-16 pt-10">
        <RegisterForm></RegisterForm>
      </div>
    </div>
  );
}

export default Register;
