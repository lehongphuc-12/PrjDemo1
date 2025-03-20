/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        updateActiveUsers(se, 1);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        updateActiveUsers(se, -1);
    }

    private void updateActiveUsers(HttpSessionEvent se, int delta) {
        Integer count = (Integer) se.getSession().getServletContext().getAttribute("activeUsers");
        if (count == null) {
            count = 0;
        }
        count += delta;
        se.getSession().getServletContext().setAttribute("activeUsers", count);
    }
}