document.addEventListener('DOMContentLoaded', () => {
            const chatbotContainer = document.getElementById('chatbot-container');
            const toggleSizeBtn = document.getElementById('chatbot-toggle-size');
            const closeBtn = document.getElementById('chatbot-close');
            const sendBtn = document.getElementById('chatbot-send');
            const input = document.getElementById('chatbot-input');
            const messages = document.getElementById('chatbot-messages');

            // Toggle size of chatbot
            toggleSizeBtn.addEventListener('click', () => {
                chatbotContainer.classList.toggle('minimized');
                // Không cần thay đổi nội dung icon nữa vì đã dùng Font Awesome
            });

            // Close chatbot
            closeBtn.addEventListener('click', () => {
                chatbotContainer.style.display = 'none';
            });

            // Send message
            sendBtn.addEventListener('click', sendMessage);
            input.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') sendMessage();
            });

            function sendMessage() {
                const message = input.value.trim();
                if (!message) return;

                // Add user message
                const userMsg = document.createElement('p');
                userMsg.classList.add('user-message');
                userMsg.textContent = message;
                messages.appendChild(userMsg);

                // Send to ChatbotServlet
                fetch('${pageContext.request.contextPath}/chatbot', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: 'message=' + encodeURIComponent(message)
                })
                .then(response => response.text())
                .then(data => {
                    // Add bot response
                    const botMsg = document.createElement('p');
                    botMsg.classList.add('bot-message');
                    botMsg.innerHTML = data; // HTML từ servlet
                    messages.appendChild(botMsg);
                    messages.scrollTop = messages.scrollHeight; // Auto scroll to bottom
                })
                .catch(error => {
                    console.error('Error:', error);
                    const errorMsg = document.createElement('p');
                    errorMsg.classList.add('bot-message');
                    errorMsg.textContent = 'Có lỗi xảy ra, vui lòng thử lại.';
                    messages.appendChild(errorMsg);
                });

                input.value = '';
            }
        });