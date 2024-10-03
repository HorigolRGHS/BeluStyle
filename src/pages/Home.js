import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import userdefault from "../assets/images/userdefault.svg"; 
import { jwtDecode } from "jwt-decode";

export function Home() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("user");


    if (token) {
      try {
        const decoded = jwtDecode(token); 
        console.log("Decoded token:", decoded);

        const username = decoded.username || "User"; 
        const avatar = decoded.user_image || userdefault; 

        setUser({ username, avatar });
      } catch (error) {
        console.error("Failed to decode token", error);
      }
    }
  }, []);

  return (
    <MainLayout>
      <h1>Hi</h1>
    </MainLayout>
  );
}
