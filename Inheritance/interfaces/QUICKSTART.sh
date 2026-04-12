#!/bin/bash
#
# Quick Start Guide for Java Interfaces Learning Package
# Author: Nitesh Kumar
# Cloud-Focused Backend Developer
#

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║     JAVA INTERFACES - QUICK START GUIDE                       ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

# Navigate to the interfaces directory
cd /home/niku/Practice/me/niteshh/OPPs/Inheritance/interfaces

echo "📁 Location: $(pwd)"
echo ""

# Show all Java files
echo "📚 Available Learning Files:"
echo "─────────────────────────────────────────────────────────────────"
ls -1 *.java | nl
echo ""

# Compilation
echo "🔨 Compiling all Java files..."
echo "─────────────────────────────────────────────────────────────────"
javac *.java 2>&1

if [ $? -eq 0 ]; then
    echo "✓ All files compiled successfully!"
    echo ""
else
    echo "✗ Compilation failed!"
    exit 1
fi

# Show compiled files
echo "✓ Compiled .class files: $(ls -1 *.class | wc -l)"
echo ""

# Display menu
echo "🎯 QUICK START OPTIONS:"
echo "─────────────────────────────────────────────────────────────────"
echo ""
echo "1. START LEARNING:"
echo "   - Read README.md for complete overview"
echo "   - Start with 01_BasicInterfaceExplanation.java"
echo "   - Follow files sequentially (01 → 08)"
echo ""
echo "2. QUICK REFERENCE:"
echo "   - Open STUDY_GUIDE.java"
echo "   - Find your topic"
echo "   - Review code snippets"
echo ""
echo "3. RUN DEMO:"
echo "   java me.niteshh.OPPs.Inheritance.interfaces.QuickTest"
echo ""
echo "4. COMPILE AGAIN:"
echo "   javac *.java"
echo ""
echo "5. VIEW FILES:"
echo "   ls -lh *.java *.md"
echo ""

# Show file count
echo "📊 Statistics:"
echo "─────────────────────────────────────────────────────────────────"
echo "Total Java source files: $(ls -1 *.java | wc -l)"
echo "Total class files: $(ls -1 *.class 2>/dev/null | wc -l)"
echo "Total lines (source): $(wc -l *.java | tail -1 | awk '{print $1}')"
echo ""

# Show README preview
echo "📖 README Preview:"
echo "─────────────────────────────────────────────────────────────────"
head -30 README.md | tail -20
echo ""
echo "... (see README.md for full content)"
echo ""

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║                                                              ║"
echo "║              Ready to Learn? Start with:                    ║"
echo "║                                                              ║"
echo "║         01_BasicInterfaceExplanation.java                   ║"
echo "║                                                              ║"
echo "║              Or read README.md first!                       ║"
echo "║                                                              ║"
echo "╚════════════════════════════════════════════════════════════════╝"

