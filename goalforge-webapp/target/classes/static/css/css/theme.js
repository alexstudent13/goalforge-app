//Change the theme of page
function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    updateThemeToggleButtonText();
}

function updateThemeToggleButtonText() {
    const currentTheme = localStorage.getItem('theme') || 'light';
    const toggleButton = document.getElementById('theme-toggle');
    const themeIcon = document.getElementById('theme-icon');
    if (currentTheme === 'dark') {
        toggleButton.textContent = "Light Mode";
        themeIcon.className = "bi bi-brightness-high-fill"; // Sun icon for light mode
    } else {
        toggleButton.textContent = "Dark Mode";
        themeIcon.className = "bi bi-moon-fill"; // Moon icon for dark mode
    }
}

document.getElementById('theme-toggle').addEventListener('click', toggleTheme);

document.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme') || 'light';
    document.documentElement.setAttribute('data-theme', savedTheme);
    updateThemeToggleButtonText();
});