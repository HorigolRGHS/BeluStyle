import React from 'react';

const MainLayout = ({ children }) => {
  return (
    <div className="flex items-center justify-center w-screen bg-white overflow-hidden"> 
      <div className="bg-white p-4 rounded"> 
        {children}
      </div>
    </div>
  );
};

export default MainLayout;
