/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        roboto: ['roboto', 'sans-serif'],
      },
      colors: {
        greyGreen: {
          50: '#738F9A',
          200: '#485F6C'
        }
      }
    },
  },
  plugins: [],
}

