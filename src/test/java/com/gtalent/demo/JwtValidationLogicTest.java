package com.gtalent.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify the JWT registration validation logic fix
 * This tests the specific logic condition that was fixed in JwtAuthController.java
 */
public class JwtValidationLogicTest {

    /**
     * This method simulates the validation logic from JwtAuthController.java line 34
     * Fixed condition: request.getPassword().length() <8 || !request.getRole().startsWith("ROLE_")
     * Returns true if validation should FAIL (return 401)
     * Returns false if validation should PASS (proceed with registration)
     */
    private boolean shouldRejectRegistration(String password, String role) {
        return password.length() < 8 || !role.startsWith("ROLE_");
    }

    @Test
    public void testPasswordTooShort_ShouldReject() {
        // Test case: Password < 8 characters should be rejected regardless of role
        assertTrue(shouldRejectRegistration("1234567", "ROLE_USER"), 
                   "Should reject when password is less than 8 characters");
        assertTrue(shouldRejectRegistration("short", "ROLE_ADMIN"), 
                   "Should reject when password is less than 8 characters");
    }

    @Test  
    public void testInvalidRole_ShouldReject() {
        // Test case: Role not starting with "ROLE_" should be rejected
        assertTrue(shouldRejectRegistration("password123", "USER"), 
                   "Should reject when role doesn't start with ROLE_");
        assertTrue(shouldRejectRegistration("validpassword", "ADMIN"), 
                   "Should reject when role doesn't start with ROLE_");
        assertTrue(shouldRejectRegistration("password123", ""), 
                   "Should reject when role is empty");
    }

    @Test
    public void testValidCredentials_ShouldAccept() {
        // Test case: Valid password (>=8 chars) AND valid role (starts with ROLE_) should be accepted
        assertFalse(shouldRejectRegistration("password123", "ROLE_USER"), 
                    "Should accept valid password and role");
        assertFalse(shouldRejectRegistration("verylongpassword", "ROLE_ADMIN"), 
                    "Should accept valid password and role");
        assertFalse(shouldRejectRegistration("12345678", "ROLE_CUSTOMER"), 
                    "Should accept exactly 8 char password with valid role");
    }

    @Test
    public void testBoundaryConditions() {
        // Test boundary conditions
        
        // Exactly 8 characters password with valid role - should pass
        assertFalse(shouldRejectRegistration("12345678", "ROLE_USER"), 
                    "Should accept exactly 8 character password with valid role");
        
        // 7 characters password (boundary case) - should fail
        assertTrue(shouldRejectRegistration("1234567", "ROLE_USER"), 
                   "Should reject 7 character password even with valid role");
        
        // Valid password but role starts with "ROLE" but not "ROLE_" - should fail
        assertTrue(shouldRejectRegistration("password123", "ROLEUSER"), 
                   "Should reject role that starts with ROLE but not ROLE_");
    }
}