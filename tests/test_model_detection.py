"""
Test model detection functionality for LuxPower integration.
"""

import pytest
from custom_components.luxpower.sensor import detect_model_code, is_12k_model, MODEL_MAP


class TestModelDetection:
    """Test model detection functions."""
    
    def test_detect_model_code_valid(self):
        """Test model code detection with valid registers."""
        registers = {7: 25955, 8: 24929}  # CEAA
        model_code = detect_model_code(registers)
        assert model_code == "CEAA"
    
    def test_detect_model_code_invalid(self):
        """Test model code detection with invalid registers."""
        registers = {7: None, 8: 24929}
        model_code = detect_model_code(registers)
        assert model_code is None
    
    def test_detect_model_code_missing(self):
        """Test model code detection with missing registers."""
        registers = {}
        model_code = detect_model_code(registers)
        assert model_code is None
    
    def test_is_12k_model_valid(self):
        """Test 12K model detection with valid models."""
        assert is_12k_model("CFAA") is True
        assert is_12k_model("CEAA") is True
        assert is_12k_model("FAAB") is True
    
    def test_is_12k_model_invalid(self):
        """Test 12K model detection with non-12K models."""
        assert is_12k_model("AAAA") is False
        assert is_12k_model("BAAA") is False
        assert is_12k_model("CBAA") is False
    
    def test_is_12k_model_none(self):
        """Test 12K model detection with None input."""
        assert is_12k_model(None) is False
        assert is_12k_model("") is False
    
    def test_model_map_completeness(self):
        """Test that all known models are in MODEL_MAP."""
        expected_models = [
            "AAAA", "AAAB", "BAAA", "BAAB", "CBAA", "EAAB", 
            "CCAA", "FAAB", "ACAB", "HAAA", "CFAA", "CEAA", 
            "BEAA", "DAAA"
        ]
        
        for model in expected_models:
            assert model in MODEL_MAP, f"Model {model} not found in MODEL_MAP"
    
    def test_model_map_values(self):
        """Test that MODEL_MAP contains expected values."""
        assert MODEL_MAP["CEAA"] == "SNA 12K-US"
        assert MODEL_MAP["CFAA"] == "SNA 12K"
        assert MODEL_MAP["FAAB"] == "LXP-LB-8-12K"
        assert MODEL_MAP["AAAA"] == "LXP 3-6K Hybrid (Standard)"
