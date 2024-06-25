// Function to switch between sections
        function showSection(sectionId) {
            // Save current section to localStorage
            localStorage.setItem('currentSection', sectionId);
            
            document.querySelectorAll('.section').forEach(section => {
                section.style.display = 'none';
            });
            document.getElementById(sectionId).style.display = 'block';
        }

        let list = document.querySelectorAll(".navigation li");

        function activeLink() {
            list.forEach((item) => {
                item.classList.remove("hovered");
            });
            this.classList.add("hovered");

            // Save active link index to localStorage
            const index = Array.from(list).indexOf(this);
            localStorage.setItem('activeLinkIndex', index);
        }

        // Add event listener for each list item
        list.forEach((item) => item.addEventListener("mouseover", activeLink));

        const menuToggle = document.querySelector('.toggle');
        const navigation = document.querySelector('.navigation');
        const main = document.querySelector('.main');

        menuToggle.addEventListener('click', () => {
            navigation.classList.toggle('active');
            main.classList.toggle('active');

            // Save navigation and main state to localStorage
            localStorage.setItem('navigationActive', navigation.classList.contains('active'));
            localStorage.setItem('mainActive', main.classList.contains('active'));
        });

        // Restore the state from localStorage on page load
        document.addEventListener('DOMContentLoaded', () => {
            // Restore the current section
            const currentSection = localStorage.getItem('currentSection');
            if (currentSection) {
                showSection(currentSection);
            } else {
                showSection('dashboard'); // Default section
            }

            // Restore active link
            const activeLinkIndex = localStorage.getItem('activeLinkIndex');
            if (activeLinkIndex !== null) {
                list[activeLinkIndex].classList.add('hovered');
            }

            // Restore navigation and main state
            const navigationActive = localStorage.getItem('navigationActive') === 'true';
            const mainActive = localStorage.getItem('mainActive') === 'true';

            // Apply classes with transitions after initial setup
            setTimeout(() => {
                if (navigationActive) {
                    navigation.classList.add('active');
                }
                if (mainActive) {
                    main.classList.add('active');
                }
                navigation.style.display = ''; // Restore default display property
            }, 100); // Delay to allow initial load without triggering animations
        });

        document.getElementById('fileInput').addEventListener('change', function () {
            var fileName = this.files[0].name;
            document.getElementById('fileName').textContent = fileName;
        });
        // Remove hash on page load
        removeHash();

        // Remove hash on hash change
        window.addEventListener('hashchange', removeHash, false);

        function removeHash() {
            if (window.location.hash) {
                let cleanURL = window.location.href.split('#')[0];
                history.replaceState(null, null, cleanURL);
            }
        }