package me.niteshh.OPPs.Inheritance.interfaces;

/**
 * ============================================================================
 * STEP 4: REAL-WORLD PRACTICAL EXAMPLE - DATABASE OPERATIONS
 * ============================================================================
 * 
 * Real-world scenario:
 * You're building a system that needs to save data to different sources:
 * - Database (MySQL, PostgreSQL)
 * - File System
 * - Cloud Storage
 * 
 * Without interfaces: You'd need different code for each storage type
 * With interfaces: Single code works for all storage types!
 * 
 * This is the POWER OF INTERFACES in real projects!
 * ============================================================================
 */

// INTERFACE: DataStorage contract - What all storage systems must do
interface DataStorage {
    /**
     * Save data to storage
     * @param key - unique identifier
     * @param value - data to save
     * @return true if successful, false otherwise
     */
    boolean save(String key, String value);
    
    /**
     * Retrieve data from storage
     * @param key - unique identifier
     * @return data if found, null otherwise
     */
    String retrieve(String key);
    
    /**
     * Delete data from storage
     * @param key - unique identifier
     * @return true if successful, false otherwise
     */
    boolean delete(String key);
    
    /**
     * Check if data exists
     */
    boolean exists(String key);
}

/**
 * CLASS: DatabaseStorage
 * ======================
 * Implementation 1: Storing data in database
 */
class DatabaseStorage implements DataStorage {
    
    private java.util.Map<String, String> database = new java.util.HashMap<>();
    
    @Override
    public boolean save(String key, String value) {
        try {
            // Simulating database save operation
            database.put(key, value);
            System.out.println("[Database] ✓ Data saved with key: " + key);
            return true;
        } catch (Exception e) {
            System.out.println("[Database] ✗ Error saving data: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String retrieve(String key) {
        String value = database.get(key);
        if (value != null) {
            System.out.println("[Database] ✓ Retrieved data for key: " + key);
        } else {
            System.out.println("[Database] ✗ Key not found: " + key);
        }
        return value;
    }
    
    @Override
    public boolean delete(String key) {
        if (database.containsKey(key)) {
            database.remove(key);
            System.out.println("[Database] ✓ Data deleted for key: " + key);
            return true;
        } else {
            System.out.println("[Database] ✗ Key not found for deletion: " + key);
            return false;
        }
    }
    
    @Override
    public boolean exists(String key) {
        return database.containsKey(key);
    }
}

/**
 * CLASS: FileStorage
 * ==================
 * Implementation 2: Storing data in files
 */
class FileStorage implements DataStorage {
    
    private java.util.Map<String, String> fileSystem = new java.util.HashMap<>();
    
    @Override
    public boolean save(String key, String value) {
        try {
            // Simulating file save operation
            fileSystem.put(key, value);
            System.out.println("[File System] ✓ Data written to file: " + key + ".txt");
            return true;
        } catch (Exception e) {
            System.out.println("[File System] ✗ Error writing file: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String retrieve(String key) {
        String value = fileSystem.get(key);
        if (value != null) {
            System.out.println("[File System] ✓ Data read from file: " + key + ".txt");
        } else {
            System.out.println("[File System] ✗ File not found: " + key + ".txt");
        }
        return value;
    }
    
    @Override
    public boolean delete(String key) {
        if (fileSystem.containsKey(key)) {
            fileSystem.remove(key);
            System.out.println("[File System] ✓ File deleted: " + key + ".txt");
            return true;
        } else {
            System.out.println("[File System] ✗ File not found: " + key + ".txt");
            return false;
        }
    }
    
    @Override
    public boolean exists(String key) {
        return fileSystem.containsKey(key);
    }
}

/**
 * CLASS: CloudStorage
 * ===================
 * Implementation 3: Storing data in cloud
 */
class CloudStorage implements DataStorage {
    
    private java.util.Map<String, String> cloudBucket = new java.util.HashMap<>();
    
    @Override
    public boolean save(String key, String value) {
        try {
            // Simulating cloud upload
            cloudBucket.put(key, value);
            System.out.println("[Cloud] ✓ Data uploaded to cloud: s3://bucket/" + key);
            return true;
        } catch (Exception e) {
            System.out.println("[Cloud] ✗ Upload failed: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String retrieve(String key) {
        String value = cloudBucket.get(key);
        if (value != null) {
            System.out.println("[Cloud] ✓ Data downloaded from cloud: s3://bucket/" + key);
        } else {
            System.out.println("[Cloud] ✗ Object not found: s3://bucket/" + key);
        }
        return value;
    }
    
    @Override
    public boolean delete(String key) {
        if (cloudBucket.containsKey(key)) {
            cloudBucket.remove(key);
            System.out.println("[Cloud] ✓ Object deleted from cloud: s3://bucket/" + key);
            return true;
        } else {
            System.out.println("[Cloud] ✗ Object not found for deletion: s3://bucket/" + key);
            return false;
        }
    }
    
    @Override
    public boolean exists(String key) {
        return cloudBucket.containsKey(key);
    }
}

/**
 * ============================================================================
 * THE POWER OF INTERFACES
 * ============================================================================
 * 
 * Look at the MAGIC below - THIS IS WHY INTERFACES ARE POWERFUL!
 * 
 * Without interfaces, you'd need:
 * ==============================
 * DatabaseStorage db = new DatabaseStorage();
 * FileStorage fs = new FileStorage();
 * CloudStorage cs = new CloudStorage();
 * 
 * db.save(...);
 * fs.save(...);
 * cs.save(...);
 * 
 * With interfaces, you can do:
 * =============================
 * DataStorage storage; // Can hold ANY implementation!
 * 
 * storage = new DatabaseStorage();
 * storage.save("key", "value");
 * 
 * storage = new FileStorage();  // Switch storage type easily
 * storage.save("key", "value");
 * 
 * storage = new CloudStorage();  // Switch again without changing code
 * storage.save("key", "value");
 * 
 * OR even better, use polymorphism:
 * ==================================
 * List<DataStorage> storages = new ArrayList<>();
 * storages.add(new DatabaseStorage());
 * storages.add(new FileStorage());
 * storages.add(new CloudStorage());
 * 
 * // Single loop works for ALL implementations!
 * for (DataStorage storage : storages) {
 *     storage.save("user123", "Nitesh Kumar");
 * }
 * 
 * This is LOOSE COUPLING!
 * If you need a new storage type later, just create new class implementing
 * DataStorage interface. NO need to change existing code!
 */

