name: Bug Report
description: Report an issue.
title: "[BUG] Brief description of the issue"
labels: ["bug"]

body:
  - type: input
    id: version
    attributes:
      label: "Version Information"
      description: "Enter the version of the integration.."
      placeholder: "Enter version here"
    validations:
      required: true

  - type: textarea
    id: images_videos
    attributes:
      label: "Media Attachments"
      description: "Provide links to any images or videos showing the issue, if applicable."
      placeholder: "Paste links to images or videos here"
    validations:
      required: true

  - type: textarea
    id: log_output
    attributes:
      label: "Log File Output"
      description: "Please add the log file output here:"
      placeholder: "Paste log output here"
      render: shell
    validations:
      required: false

  - type: textarea
    id: detailed_description
    attributes:
      label: "Detailed Description"
      description: "Provide a detailed description of the issue, including steps to reproduce and the expected behavior."
      placeholder: "Describe the issue in detail"
    validations:
      required: true
  - type: checkboxes
    id: issue_check
    attributes:
      label: Issues
      description: By Submitting this Issue you confirm you have checked to see if the issue already exists and your not duplicating the bug, if this is found to be the case this will be deleted 
      options:
        - label: I agree i have checked the Issues Tab to see if this already Exists
          required: true
