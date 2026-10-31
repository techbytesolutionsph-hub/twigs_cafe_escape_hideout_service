CREATE SCHEMA IF NOT EXISTS the_twigs_escape_hideout_db;

CREATE SEQUENCE IF NOT EXISTS the_twigs_escape_hideout_db.app_user_id_seq
    START WITH 1
    INCREMENT BY 1;


-- =====================================================
-- THE TWIGS ROLE-PERMISSION MAPPING
-- Insert only if the role + permission combination
-- does not already exist.
-- =====================================================

INSERT INTO tteh_app_role_mapping (
    role,
    permission_code,
    description
)
SELECT
    v.role,
    v.permission_code,
    v.description
FROM (
         VALUES

             -- =====================================================
             -- SUPER_ADMIN
             -- Full system access
             -- =====================================================

             ('SUPER_ADMIN', 'FULL_ACCESS', 'Full system access with permission to manage all modules'),
             ('SUPER_ADMIN', 'VIEW_SYSTEM_ADMINISTRATION', 'View System Administration'),

             -- =====================================================
             -- ADMIN
             -- Administrative and business management
             -- =====================================================

             ('ADMIN', 'VIEW_DASHBOARD', 'View dashboard'),
             ('ADMIN', 'MANAGE_USERS', 'Manage system users'),
             ('ADMIN', 'MANAGE_ROLES', 'Manage user roles and permissions'),
             ('ADMIN', 'VIEW_STAFF', 'View staff directory'),
             ('ADMIN', 'MANAGE_STAFF', 'Manage staff records'),
             ('ADMIN', 'MANAGE_STAFF_SCHEDULE', 'Manage staff schedules'),

             ('ADMIN', 'VIEW_SALES_REPORT', 'View sales reports'),
             ('ADMIN', 'VIEW_FINANCIAL_REPORT', 'View financial reports'),
             ('ADMIN', 'VIEW_REVENUE', 'View business revenue'),
             ('ADMIN', 'VIEW_EXPENSE', 'View business expenses'),
             ('ADMIN', 'MANAGE_EXPENSE', 'Manage business expenses'),
             ('ADMIN', 'VIEW_OPERATIONAL_REPORT', 'View operational reports'),
             ('ADMIN', 'EXPORT_REPORT', 'Export business reports'),

             ('ADMIN', 'ACCESS_POS', 'Access point of sale'),
             ('ADMIN', 'VIEW_TRANSACTIONS', 'View sales transactions'),
             ('ADMIN', 'VOID_TRANSACTION', 'Void completed transactions'),
             ('ADMIN', 'REFUND_PAYMENT', 'Process customer refunds'),

             ('ADMIN', 'VIEW_MENU', 'View products and menu'),
             ('ADMIN', 'MANAGE_MENU', 'Manage menu items'),
             ('ADMIN', 'MANAGE_PRODUCT_PRICING', 'Manage product pricing'),

             ('ADMIN', 'VIEW_INVENTORY', 'View inventory records'),
             ('ADMIN', 'MANAGE_INVENTORY', 'Manage inventory records'),
             ('ADMIN', 'RECEIVE_STOCK', 'Record received stock'),
             ('ADMIN', 'ADJUST_STOCK', 'Adjust inventory quantities'),
             ('ADMIN', 'VIEW_STOCK_REPORT', 'View inventory reports'),
             ('ADMIN', 'MANAGE_SUPPLIERS', 'Manage supplier records'),

             ('ADMIN', 'MANAGE_RECIPES', 'Manage food recipes'),
             ('ADMIN', 'MANAGE_BEVERAGE_RECIPES', 'Manage beverage recipes'),
             ('ADMIN', 'MANAGE_DAILY_OPERATIONS', 'Manage daily business operations'),
             ('ADMIN', 'MANAGE_OPENING_CLOSING', 'Manage store opening and closing procedures'),

             -- =====================================================
             -- PARTNER
             -- Business monitoring and reports
             -- =====================================================

             ('PARTNER', 'VIEW_DASHBOARD', 'View business dashboard'),
             ('PARTNER', 'VIEW_SALES_REPORT', 'View sales reports'),
             ('PARTNER', 'VIEW_FINANCIAL_REPORT', 'View financial reports'),
             ('PARTNER', 'VIEW_REVENUE', 'View business revenue'),
             ('PARTNER', 'VIEW_EXPENSE', 'View business expenses'),
             ('PARTNER', 'VIEW_OPERATIONAL_REPORT', 'View operational reports'),
             ('PARTNER', 'VIEW_STOCK_REPORT', 'View inventory reports'),
             ('PARTNER', 'VIEW_INVENTORY', 'View inventory records'),
             ('PARTNER', 'VIEW_TRANSACTIONS', 'View sales transactions'),
             ('PARTNER', 'EXPORT_REPORT', 'Export business reports'),

             -- =====================================================
             -- OPERATIONS_MANAGER
             -- Overall daily business operations
             -- =====================================================

             ('OPERATIONS_MANAGER', 'VIEW_DASHBOARD', 'View dashboard'),
             ('OPERATIONS_MANAGER', 'VIEW_STAFF', 'View staff directory'),
             ('OPERATIONS_MANAGER', 'MANAGE_STAFF', 'Manage staff records'),
             ('OPERATIONS_MANAGER', 'MANAGE_STAFF_SCHEDULE', 'Manage staff schedules'),

             ('OPERATIONS_MANAGER', 'VIEW_SALES_REPORT', 'View sales reports'),
             ('OPERATIONS_MANAGER', 'VIEW_FINANCIAL_REPORT', 'View financial reports'),
             ('OPERATIONS_MANAGER', 'VIEW_REVENUE', 'View business revenue'),
             ('OPERATIONS_MANAGER', 'VIEW_EXPENSE', 'View business expenses'),
             ('OPERATIONS_MANAGER', 'MANAGE_EXPENSE', 'Manage business expenses'),
             ('OPERATIONS_MANAGER', 'VIEW_OPERATIONAL_REPORT', 'View operational reports'),
             ('OPERATIONS_MANAGER', 'EXPORT_REPORT', 'Export business reports'),

             ('OPERATIONS_MANAGER', 'ACCESS_POS', 'Access point of sale'),
             ('OPERATIONS_MANAGER', 'CREATE_ORDER', 'Create customer orders'),
             ('OPERATIONS_MANAGER', 'UPDATE_ORDER', 'Update customer orders'),
             ('OPERATIONS_MANAGER', 'CANCEL_ORDER', 'Cancel customer orders'),
             ('OPERATIONS_MANAGER', 'APPLY_DISCOUNT', 'Apply discounts to orders'),
             ('OPERATIONS_MANAGER', 'PROCESS_PAYMENT', 'Process customer payments'),
             ('OPERATIONS_MANAGER', 'VIEW_TRANSACTIONS', 'View sales transactions'),
             ('OPERATIONS_MANAGER', 'VOID_TRANSACTION', 'Void completed transactions'),
             ('OPERATIONS_MANAGER', 'REFUND_PAYMENT', 'Process customer refunds'),
             ('OPERATIONS_MANAGER', 'MANAGE_CASH_DRAWER', 'Manage cash drawer'),

             ('OPERATIONS_MANAGER', 'VIEW_MENU', 'View products and menu'),
             ('OPERATIONS_MANAGER', 'MANAGE_MENU', 'Manage menu items'),
             ('OPERATIONS_MANAGER', 'MANAGE_PRODUCT_PRICING', 'Manage product pricing'),

             ('OPERATIONS_MANAGER', 'VIEW_INVENTORY', 'View inventory records'),
             ('OPERATIONS_MANAGER', 'MANAGE_INVENTORY', 'Manage inventory records'),
             ('OPERATIONS_MANAGER', 'RECEIVE_STOCK', 'Record received stock'),
             ('OPERATIONS_MANAGER', 'ADJUST_STOCK', 'Adjust inventory quantities'),
             ('OPERATIONS_MANAGER', 'VIEW_STOCK_REPORT', 'View inventory reports'),
             ('OPERATIONS_MANAGER', 'MANAGE_SUPPLIERS', 'Manage supplier records'),

             ('OPERATIONS_MANAGER', 'VIEW_KITCHEN_ORDERS', 'View kitchen orders'),
             ('OPERATIONS_MANAGER', 'UPDATE_KITCHEN_ORDER_STATUS', 'Update kitchen order status'),
             ('OPERATIONS_MANAGER', 'MANAGE_KITCHEN_QUEUE', 'Manage kitchen order queue'),

             ('OPERATIONS_MANAGER', 'VIEW_SERVICE_ORDERS', 'View service orders'),
             ('OPERATIONS_MANAGER', 'UPDATE_SERVICE_ORDER_STATUS', 'Update service order status'),
             ('OPERATIONS_MANAGER', 'MANAGE_TABLES', 'Manage dining tables'),

             ('OPERATIONS_MANAGER', 'VIEW_BEVERAGE_ORDERS', 'View beverage orders'),
             ('OPERATIONS_MANAGER', 'UPDATE_BEVERAGE_ORDER_STATUS', 'Update beverage order status'),

             ('OPERATIONS_MANAGER', 'MANAGE_DAILY_OPERATIONS', 'Manage daily business operations'),
             ('OPERATIONS_MANAGER', 'MANAGE_OPENING_CLOSING', 'Manage store opening and closing procedures'),

             -- =====================================================
             -- HEAD_COOK
             -- Kitchen department management
             -- =====================================================

             ('HEAD_COOK', 'VIEW_DASHBOARD', 'View kitchen dashboard'),
             ('HEAD_COOK', 'VIEW_KITCHEN_ORDERS', 'View kitchen orders'),
             ('HEAD_COOK', 'UPDATE_KITCHEN_ORDER_STATUS', 'Update kitchen order status'),
             ('HEAD_COOK', 'MANAGE_KITCHEN_QUEUE', 'Manage kitchen order queue'),
             ('HEAD_COOK', 'VIEW_RECIPES', 'View food recipes'),
             ('HEAD_COOK', 'MANAGE_RECIPES', 'Manage food recipes'),
             ('HEAD_COOK', 'VIEW_INVENTORY', 'View inventory records'),
             ('HEAD_COOK', 'MANAGE_INVENTORY', 'Manage kitchen inventory'),
             ('HEAD_COOK', 'RECEIVE_STOCK', 'Record received kitchen stock'),
             ('HEAD_COOK', 'ADJUST_STOCK', 'Adjust kitchen inventory quantities'),
             ('HEAD_COOK', 'VIEW_STOCK_REPORT', 'View inventory reports'),

             -- =====================================================
             -- KITCHEN_STAFF
             -- Kitchen order processing
             -- =====================================================

             ('KITCHEN_STAFF', 'VIEW_KITCHEN_ORDERS', 'View kitchen orders'),
             ('KITCHEN_STAFF', 'UPDATE_KITCHEN_ORDER_STATUS', 'Update kitchen order status'),
             ('KITCHEN_STAFF', 'VIEW_RECIPES', 'View food recipes'),

             -- =====================================================
             -- SERVICE_CREW
             -- Customer service and table operations
             -- =====================================================

             ('SERVICE_CREW', 'VIEW_SERVICE_ORDERS', 'View customer service orders'),
             ('SERVICE_CREW', 'UPDATE_SERVICE_ORDER_STATUS', 'Update service order status'),
             ('SERVICE_CREW', 'MANAGE_TABLES', 'Manage dining tables'),
             ('SERVICE_CREW', 'CREATE_ORDER', 'Create customer orders'),
             ('SERVICE_CREW', 'VIEW_MENU', 'View products and menu'),

             -- =====================================================
             -- BARISTA
             -- Beverage preparation and order processing
             -- =====================================================

             ('BARISTA', 'VIEW_BEVERAGE_ORDERS', 'View beverage orders'),
             ('BARISTA', 'UPDATE_BEVERAGE_ORDER_STATUS', 'Update beverage order status'),
             ('BARISTA', 'VIEW_RECIPES', 'View beverage recipes'),
             ('BARISTA', 'VIEW_MENU', 'View products and menu'),

             -- =====================================================
             -- CASHIER
             -- POS and payment processing
             -- =====================================================

             ('CASHIER', 'VIEW_DASHBOARD', 'View cashier dashboard'),
             ('CASHIER', 'ACCESS_POS', 'Access point of sale'),
             ('CASHIER', 'CREATE_ORDER', 'Create customer orders'),
             ('CASHIER', 'UPDATE_ORDER', 'Update customer orders'),
             ('CASHIER', 'APPLY_DISCOUNT', 'Apply authorized discounts'),
             ('CASHIER', 'PROCESS_PAYMENT', 'Process customer payments'),
             ('CASHIER', 'ISSUE_RECEIPT', 'Issue transaction receipts'),
             ('CASHIER', 'VIEW_TRANSACTIONS', 'View sales transactions'),
             ('CASHIER', 'VIEW_OWN_SHIFT', 'View own cashier shift'),
             ('CASHIER', 'MANAGE_CASH_DRAWER', 'Manage cash drawer'),
             ('CASHIER', 'VIEW_MENU', 'View products and menu')

     ) AS v(role, permission_code, description)

WHERE NOT EXISTS (
    SELECT 1
    FROM tteh_app_role_mapping existing
    WHERE existing.role = v.role
      AND existing.permission_code = v.permission_code
);