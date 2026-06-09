#!/bin/bash

# ==========================================
# Advanced Collection Patterns - Compile & Run Script
# ==========================================

echo "🚀 Advanced Collection Patterns - Compilation Script"
echo "======================================================"
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counter for success/failure
success=0
failed=0

# List of completed files
files=(
    "01_CachingPatterns.java"
    "02_RepositoryPattern.java"
    "03_ObserverPattern.java"
    "04_StrategyPattern.java"
    "05_ProducerConsumer.java"
    "06_ThreadSafeCollections.java"
    "07_LockFreeDataStructures.java"
)

# Function to compile a file
compile_file() {
    local file=$1
    echo -n "Compiling $file... "
    
    if javac "$file" 2>/dev/null; then
        echo -e "${GREEN}✅ SUCCESS${NC}"
        ((success++))
        return 0
    else
        echo -e "${RED}❌ FAILED${NC}"
        ((failed++))
        javac "$file" # Show errors
        return 1
    fi
}

# Function to run a class
run_file() {
    local java_file=$1
    local class_name="${java_file%.java}"
    
    echo -e "${YELLOW}Running $class_name...${NC}"
    echo "----------------------------------------"
    
    if java "$class_name" 2>&1; then
        echo -e "${GREEN}✅ Executed successfully${NC}"
    else
        echo -e "${RED}❌ Runtime error${NC}"
    fi
    
    echo ""
}

# Main compilation loop
echo "📦 Compiling files..."
echo ""

for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        compile_file "$file"
    else
        echo -e "${YELLOW}⚠️  $file not found (skipping)${NC}"
    fi
done

echo ""
echo "======================================================"
echo "📊 Compilation Results:"
echo "   ✅ Success: $success"
echo "   ❌ Failed: $failed"
echo "======================================================"
echo ""

# If all compiled successfully, ask to run
if [ $failed -eq 0 ] && [ $success -gt 0 ]; then
    echo -e "${GREEN}All files compiled successfully!${NC}"
    echo ""
    read -p "Do you want to run the examples? (y/n): " -n 1 -r
    echo ""
    
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo ""
        echo "🏃 Running examples..."
        echo "======================================================"
        echo ""
        
        for file in "${files[@]}"; do
            if [ -f "$file" ]; then
                run_file "$file"
            fi
        done
    fi
fi

# Cleanup .class files
read -p "Clean up .class files? (y/n): " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    rm -f *.class
    echo -e "${GREEN}✅ Cleaned up .class files${NC}"
fi

echo ""
echo "Done! 🎉"
