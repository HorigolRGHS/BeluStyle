/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        poppins: ['Poppins', 'san-serif'],
        montserrat: ['Montserrat', 'san-serif'],
        ropaSans: ['Ropa Sans', 'san-serif']
      },
    },
  },
  plugins: [],
}