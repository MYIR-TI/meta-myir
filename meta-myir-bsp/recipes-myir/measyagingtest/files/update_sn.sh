#!/bin/sh

# Check input file
if [ $# -ne 1 ]; then
    echo "Usage: $0 <json_file>"
    exit 1
fi

json_file="$1"
temp_file="${json_file}.tmp"
found_empty_sn=0

# Function to generate random SN (WK prefix + 15 digits)
generate_random_sn() {
    # Generate WK followed by 15 digits
    echo -n "WK"
    for (( i=0; i<15; i++ )); do
        echo -n $((RANDOM % 10))
    done
    echo
}

# First check for empty SN fields
while IFS= read -r line; do
    if [[ "$line" =~ '"SN": ""' || "$line" =~ '"SN":""' ]]; then
        found_empty_sn=1
        break
    fi
done < "$json_file"

if [ "$found_empty_sn" -eq 0 ]; then
    echo "No empty SN fields found."
    exit 0
fi

# Prompt user
echo "Warning: Empty SN field detected! Generating random SN values..."

# Process JSON file
echo "Processing file..."
while IFS= read -r line || [ -n "$line" ]; do  # Added || [ -n "$line" ] to catch last line
    if [[ "$line" =~ '"SN": ""' || "$line" =~ '"SN":""' ]]; then
        # Capture original indentation
        indentation="${line%%\"SN\"*}"
        random_sn=$(generate_random_sn)
        echo "${indentation}\"SN\": \"$random_sn\"," >&2  # Show generation message to stderr
        echo "${indentation}\"SN\": \"$random_sn\","
    else
        echo "$line"
    fi
done < "$json_file" > "$temp_file" && mv "$temp_file" "$json_file"

echo "Processing complete."