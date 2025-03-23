document.addEventListener('DOMContentLoaded', () => {
            // Khởi tạo Coze Web SDK
            let chatbot;
            try {
                chatbot = new CozeWebSDK.WebChatClient({
                    config: {
                        bot_id: '7484574725558779922',
                    },
                    componentProps: {
                        title: 'Chatbot Hỗ Trợ',
                        layout: 'pc',
                    },
                    auth: {
                        type: 'token',
                        token: 'pat_OSEe94aKtxWkLLz1RxkCVvp7AccWrwC8tXhIOUIN9hKm7Od50mMmVnoVaRaQVfzK', // Cập nhật token nếu cần
                        onRefreshToken: function () {
                            return 'pat_OSEe94aKtxWkLLz1RxkCVvp7AccWrwC8tXhIOUIN9hKm7Od50mMmVnoVaRaQVfzK'; // Cập nhật token nếu cần
                        }
                    }
                });
            } catch (error) {
                console.error('Error initializing chatbot:', error);
            }
        });