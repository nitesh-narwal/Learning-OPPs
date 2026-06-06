#!/bin/bash

# Script to compile all Iterator tutorial files
# Run this to check for compilation errors

echo "=== Compiling Iterator Tutorial Files ==="
echo ""

files=(
    "01_IteratorBasics.java"
    "02_IteratorVsForLoop.java"
    "03_IteratorMethods.java"
    "04_ListIterator.java"
    "05_FailFast_FailSafe.java"
    "06_CommonMistakes.java"
    "07_CustomIterator.java"
    "08_IndustryPatterns.java"
    "09_PerformanceOptimization.java"
    "10_ModernAlternatives.java"
)

success=0
failed=0

for file in "${files[@]}"; do
    echo "Compiling $file..."
    if javac "$file" 2>/dev/null; then
        echo "  ✅ Success"
        ((success++))
    else
        echo "  ❌ Failed"
        ((failed++))
        javac "$file"
    fi
    echo ""
done

echo "==================================="
echo "Results: $success succeeded, $failed failed"
echo "==================================="

# Clean up .class files
rm -f *.class
