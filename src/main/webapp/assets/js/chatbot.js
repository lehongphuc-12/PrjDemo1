document.addEventListener('DOMContentLoaded', () => {
            // Khởi tạo Coze Web SDK
            let chatbot;
            try {
                chatbot = new CozeWebSDK.WebChatClient({
                    config: {
                        bot_id: '7485039828631961607',
                    },
                    componentProps: {
                        title: 'Chatbot Hỗ Trợ',
                        layout: 'pc',
                    },
                    auth: {
                        type: 'token',
                        token: 'pat_XAGuOBXIf6l0ko5TBNEjtCbhp101dZrRPuN75deReBKcFMpjd9IUAK9JiwCLDkXW', // Cập nhật token nếu cần
                        onRefreshToken: function () {
                            return 'pat_XAGuOBXIf6l0ko5TBNEjtCbhp101dZrRPuN75deReBKcFMpjd9IUAK9JiwCLDkXW'; // Cập nhật token nếu cần
                        }
                    }
                });
            } catch (error) {
                console.error('Error initializing chatbot:', error);
            }
        });